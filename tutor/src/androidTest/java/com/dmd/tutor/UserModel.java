package com.dmd.tutor;

import com.dmd.tutor.sqlite.Table;
import com.dmd.tutor.sqlite.Table.Column;

import java.util.Arrays;
import java.util.Date;


@Table(name="t_user",version=1)
public class UserModel {
	@Table.Column(name="user_id",type=Column.TYPE_INTEGER,isPrimaryKey=true)
	public Integer userId;
	
	@Table.Column(name="id_card",type=Column.TYPE_LONG,isUnique=true)
	public Long idCard;
	
	@Table.Column(name="user_name",type=Column.TYPE_STRING,isNull=false)
	public String userName;
	
	@Table.Column(name="born_date",type=Column.TYPE_TIMESTAMP)
	public Date bornDate;
	
	@Table.Column(name="pictrue",type=Column.TYPE_BLOB)
	public byte[] pictrue;
	
	@Table.Column(name="is_login",type=Column.TYPE_BOOLEAN)
	public Boolean isLogin;
	
	@Table.Column(name="weight",type=Column.TYPE_DOUBLE)
	public Double weight;

	@Table.Column(name="new_column_1",type=Column.TYPE_INTEGER)
	public Integer newColumn;
	
	@Table.Column(name="new_column_2",type=Column.TYPE_INTEGER)
	public Integer newColumn2;
	
	@Table.Column(name="looooooggggggggggggggggggg_name",type=Column.TYPE_INTEGER)
	public Integer longName;
	
	@Table.Column(name="description",type=Column.TYPE_STRING)
	public String article;
	
	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", userName=" + userName
				+ ", bornDate=" + bornDate + ", pictrue="
				+ Arrays.toString(pictrue) + ", isLogin=" + isLogin
				+ ", weight=" + weight + "]";
	}
}
