package com.eduardo.ekki.ekkiTransfer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.eduardo.ekki.ekkiTransfer.entity.CreditCard;

@RepositoryRestResource
public interface CreditCardCrud extends CrudRepository<CreditCard, Long>{

}
