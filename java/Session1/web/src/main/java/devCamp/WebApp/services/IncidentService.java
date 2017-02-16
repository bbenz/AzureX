package devCamp.WebApp.services;

import devCamp.WebApp.models.IncidentBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public interface IncidentService {

    List<IncidentBean> getAllIncidents();

    IncidentBean createIncident(IncidentBean incident);

    IncidentBean updateIncident(IncidentBean newIncident);
    
    IncidentBean getById(String incidentId);

    @Async
    CompletableFuture<List<IncidentBean>> getAllIncidentsAsync();

    @Async
    CompletableFuture<IncidentBean> createIncidentAsync(IncidentBean incident);

    @Async
    CompletableFuture<IncidentBean> updateIncidentAsync(IncidentBean newIncident);

    @Async
    CompletableFuture<IncidentBean> getByIdAsync(String incidentId);

    void clearCache();
}
