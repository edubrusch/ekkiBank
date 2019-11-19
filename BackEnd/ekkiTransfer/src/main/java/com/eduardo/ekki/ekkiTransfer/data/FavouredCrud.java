package com.eduardo.ekki.ekkiTransfer.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.eduardo.ekki.ekkiTransfer.domain.Favoured;

@RepositoryRestResource
public interface FavouredCrud extends CrudRepository<Favoured, Long> {

}
