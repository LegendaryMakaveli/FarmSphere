    package com.farmSphere.auth.data.repository;

    import com.farmSphere.auth.data.model.Farmer;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;
    import com.farmSphere.core.enums.REGISTRATION_STATUS;

    import java.util.List;
    import java.util.Optional;

    @Repository
    public interface FarmerRepository extends JpaRepository<Farmer, Long> {
        Optional<Farmer> findByUserEmail(String email);

        Optional<Farmer> findByUserPhoneNumber(String phoneNumber);

        List<Farmer> findAllByRegistrationStatus(REGISTRATION_STATUS status);
    }