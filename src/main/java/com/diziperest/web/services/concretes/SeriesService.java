package com.diziperest.web.services.concretes;

import com.diziperest.web.models.Series;
import com.diziperest.web.repositories.SeriesRepository;
import com.diziperest.web.services.abstracts.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeriesService implements ISeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public Series save(Series serie) {
        String fileName = storageService.store(serie.getPhoto(),serie.getName());
        serie.setPhotoUrl(fileName);
        return seriesRepository.save(serie);
    }
}
