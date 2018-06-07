package com.medicine.model.dao;

import java.util.List;

import com.medicine.model.entity.MisUser;

public interface IMisUserDAO {
	public boolean add(MisUser misUser);

	public boolean remove(String Id);

	public boolean modify(MisUser misUser);

	public MisUser findById(String Id);

	public List<MisUser> findByLike(MisUser misUser);
}
