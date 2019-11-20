package com.eduardo.ekki.ekkiTransfer.data.fullCrud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.eduardo.ekki.ekkiTransfer.domain.CreditCard;

@RepositoryRestResource
public interface CreditCardCrud extends CrudRepository<CreditCard, Long>{

}
