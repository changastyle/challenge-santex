package com.santex.profile.util;

import lombok.*;

@Data @Builder @AllArgsConstructor
public class ClaveValor
{
    private String clave;
    private Object valor;

    public String toString()
    {
        return clave + ": " + valor;
    }
}
