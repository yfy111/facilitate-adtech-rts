package com.adtech.rts.model.entity.base;

import lombok.Data;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
    private ObjectId _id;
}
