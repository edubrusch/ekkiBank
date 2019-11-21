package com.eduardo.ekki.ekkiTransfer.data.fullCrud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.eduardo.ekki.ekkiTransfer.entity.Favoured;

@RepositoryRestResource
public interface FavouredCrud extends CrudRepository<Favoured, Long> {

}
