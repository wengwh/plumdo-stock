package com.plumdo.repository;


import com.plumdo.domain.SystemParameter;

public interface SystemParameterRepository extends BaseRepository<SystemParameter, Integer> {
	SystemParameter findFirstByParameterName(String parameterName);
}