package com.group1.ecocredit.repositories;

import com.group1.ecocredit.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
