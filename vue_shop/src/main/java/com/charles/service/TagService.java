package com.charles.service;

import com.charles.entity.Tag;

public interface TagService {
    void save(Tag tag);

    void delete(Integer id);
}
