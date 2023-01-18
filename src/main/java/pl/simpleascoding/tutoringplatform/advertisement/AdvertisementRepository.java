package pl.simpleascoding.tutoringplatform.advertisement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Page<Advertisement> findAllByAuthor_NameOrderByCreationDateDesc(String username, Pageable pageable);
}
