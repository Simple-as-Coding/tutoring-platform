package pl.simpleascoding.tutoringplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.simpleascoding.tutoringplatform.domain.advertisement.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
