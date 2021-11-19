package ciclo3.reto3.service;

import ciclo3.reto3.model.Cloud;
import ciclo3.reto3.repository.CloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CloudService {

    @Autowired
    private CloudRepository cloudRepository;

    public List<Cloud> getAll() {
        return cloudRepository.getAll();
    }

    public Optional<Cloud> getCloud(int id) {
        return cloudRepository.getCloud(id);
    }

    public Cloud save(Cloud cloud) {
        if (cloud.getId() == null) {
            return cloudRepository.save(cloud);
        } else {
            Optional<Cloud> tmpCloud = cloudRepository.getCloud(cloud.getId());
            if (tmpCloud.isEmpty()) {
                return cloudRepository.save(cloud);
            } else {
                return cloud;
            }
        }
    }

    public Cloud update(Cloud cloud) {
        if (cloud.getId() != null) {
            Optional<Cloud> tmpCloud = cloudRepository.getCloud(cloud.getId());
            if (!tmpCloud.isEmpty()) {
                if (cloud.getName() != null) {
                    tmpCloud.get().setName(cloud.getName());
                }
                if (cloud.getBrand() != null) {
                    tmpCloud.get().setBrand(cloud.getBrand());
                }
                if (cloud.getYear()!= null) {
                    tmpCloud.get().setYear(cloud.getYear());
                }
                if (cloud.getDescription()!= null) {
                    tmpCloud.get().setDescription(cloud.getDescription());
                }
                cloudRepository.save(tmpCloud.get());
                return tmpCloud.get();
            } else {
                return cloud;
            }
        } else {
            return cloud;
        }

    }

    public boolean deleteCloud(int id) {
        Boolean result = getCloud(id).map(cloud -> {
            cloudRepository.delete(cloud);
            return true;
        }).orElse(false);
        return result;
    }

}
