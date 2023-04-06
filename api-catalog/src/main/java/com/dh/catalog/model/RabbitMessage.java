package com.dh.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMessage {
    private String id;
    private AudiovisualType type;
    private String genre;

    private String obj;

    private enum AudiovisualType { MOVIE , SERIE }
}
