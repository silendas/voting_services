package com.service.voting.common.reuse;

import java.lang.reflect.Field;

public class PatchField<T> {

    public T fusion(T existing, T req) {
        Field[] fieldUsers = req.getClass().getDeclaredFields();
        for (Field field : fieldUsers) {
            field.setAccessible(true);
            try {
                Object value = field.get(req);
                if (value != null) {
                    Field existingField = existing.getClass().getDeclaredField(field.getName());
                    existingField.setAccessible(true);

                    if (existingField.getType() == boolean.class || existingField.getType() == Boolean.class) {
                        boolean existingValue = existingField.getBoolean(existing);
                        boolean reqValue = (boolean) value;

                        if (existingValue || reqValue) {
                            existingField.set(existing, true);
                        }
                    } else {
                        existingField.set(existing, value);
                    }
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return existing;
    }
}

// public class PatchField<T> {

// public T fusion(T existing, T req) {
// Field[] fieldUsers = req.getClass().getDeclaredFields();
// for (Field field : fieldUsers) {
// field.setAccessible(true);
// try {
// Object value = field.get(req);
// if (value != null) {
// Field userField = existing.getClass().getDeclaredField(field.getName());
// userField.setAccessible(true);
// userField.set(existing, value); // Perbaikan di sini, set nilai ke objek
// existing
// }
// } catch (IllegalAccessException | NoSuchFieldException e) {
// e.printStackTrace();
// }
// }
// return existing;
// }
// }
