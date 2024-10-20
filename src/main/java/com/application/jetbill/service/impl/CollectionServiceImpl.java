package com.application.jetbill.service.impl;


import com.application.jetbill.exception.ResourceNotFoundException;
import com.application.jetbill.model.entity.Collection;
import com.application.jetbill.repository.CollectionRepository;
import com.application.jetbill.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;

    @Override
    public Collection createCollection(Collection collection) {
        collection.setCreatedAt(LocalDateTime.now());
        return collectionRepository.save(collection);
    }

    @Override
    public List<Collection> getCollectionsByUser(Integer userId) {
        return collectionRepository.findByCustomerId(userId);
    }

    @Override
    public Collection getCollectionById(Integer collectionId) {
        return collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found"));
    }

    @Override
    @Transactional
    public Collection updateCollection(Integer collectionId, Collection updatedCollection) {
        Collection existingCollection = getCollectionById(collectionId);
        existingCollection.setName(updatedCollection.getName());
        existingCollection.setUpdatedAt(LocalDateTime.now());
        return collectionRepository.save(existingCollection);
    }

    @Override
    @Transactional
    public void deleteCollection(Integer collectionId) {
        Collection collection = getCollectionById(collectionId);
        collectionRepository.delete(collection);
    }
}
