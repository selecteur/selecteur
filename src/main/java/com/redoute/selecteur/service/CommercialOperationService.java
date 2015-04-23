package com.redoute.selecteur.service;

import com.redoute.selecteur.domain.CommercialOperation;
import com.redoute.selecteur.domain.DetailedCommercialOperation;
import com.redoute.selecteur.repository.CommercialOperationRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommercialOperationService {

    private final Logger log = LoggerFactory.getLogger(CommercialOperationService.class);

    @Inject
    private CommercialOperationRepository commercialOperationRepository;

    public void save(DetailedCommercialOperation detailedCommercialOperation) {
        log.debug("Save DetailedCommercialOperation {}", detailedCommercialOperation);
        CommercialOperation commercialOperation = new CommercialOperation();
        commercialOperation.setId(detailedCommercialOperation.getId());
        commercialOperation.setProviderCode(detailedCommercialOperation.getProviderCode());
        commercialOperation.setDomain(detailedCommercialOperation.getDomain());

        log.debug("Persist CommercialOperation {}", commercialOperation);
        commercialOperationRepository.save(commercialOperation);
        detailedCommercialOperation.setId(commercialOperation.getId());
    }

    public void delete(Long id) {
        commercialOperationRepository.delete(id);
    }

    public DetailedCommercialOperation findById(Long id) {
        log.debug("Find by id {}", id);

        CommercialOperation commercialOperation = commercialOperationRepository.findOne(id);
        if (commercialOperation == null) {
            return null;
        }

        // TODO: Build request

        // TODO: Read response
        DetailedCommercialOperation detailedCommercialOperation = new DetailedCommercialOperation(commercialOperation);
        detailedCommercialOperation.setDescription("Mock description for opcom " + id);
        detailedCommercialOperation.setStartDate(new DateTime(2015, 4, 23, 12, 30));
        detailedCommercialOperation.setEndDate(new DateTime(2015, 6, 12, 16, 0));

        return detailedCommercialOperation;
    }

    public Page<DetailedCommercialOperation> findByCriteria(Pageable pageable) {
        log.debug("Find by criteria {}");

        Sort sort = pageable.getSort();
        if (sort != null) {
            for (Sort.Order order : sort) {
                log.info("Sort by {}", order.getProperty(), order.getDirection());
            }
        }

        // TODO: Build request

        // TODO: Read response
        Page<CommercialOperation> page = commercialOperationRepository.findAll(pageable);

        List<DetailedCommercialOperation> content = new ArrayList<>();
        for (CommercialOperation commercialOperation : page.getContent()) {
            DetailedCommercialOperation detailedCommercialOperation = new DetailedCommercialOperation(commercialOperation);
            detailedCommercialOperation.setDescription("Mock description for opcom " + commercialOperation.getId());
            detailedCommercialOperation.setStartDate(new DateTime(2015, 4, 23, 12, 30));
            detailedCommercialOperation.setEndDate(new DateTime(2015, 6, 12, 16, 0));
            content.add(detailedCommercialOperation);
        }
        long total = page.getTotalElements();

        return new PageImpl<>(content, pageable, total);
    }
}
