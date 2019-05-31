package com.macro.springboot.generic;

import com.macro.springboot.utils.MybatisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.macro.springboot.utils.MybatisUtils.camelToUnderline;

public abstract class BaseSqlProvider<T, ID> {
	protected Class<T> entityClass;
	protected Class<ID> idClass;

	protected Logger logger;

	private String className;
	private String tableName;

	/*
	* cache缓存
	* */
	private Field idField;
	private String idColumnName;

	private List<Field> fieldsWithoutId = new ArrayList<>();
	private List<String> columnNamesWithoutId = new ArrayList<>();

	private List<Field> fields = new ArrayList<>();
	private List<String> columnNames = new ArrayList<>();

	@SuppressWarnings({"unchecked","rawtypes"})
	public BaseSqlProvider(){
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType){
			this.entityClass = (Class<T>)((ParameterizedType)type).getActualTypeArguments()[0];
			this.idClass = (Class<ID>)((ParameterizedType)type).getActualTypeArguments()[1];
		}else {
			this.entityClass = null;
		}

		//log
		logger = LoggerFactory.getLogger(entityClass);

		className = entityClass.getSimpleName();

		//解析表名
		Table tableName = entityClass.getAnnotation(Table.class);
		this.tableName = camelToUnderline(className);
		if (tableName != null){
			if (!StringUtils.isEmpty(tableName.name())){
				this.tableName = tableName.name();
			}
		}

		//没有主键
		//属性
		this.fieldsWithoutId = MybatisUtils.getAllFieldWithoutIdByClass(entityClass);

		for (Field field : this.fieldsWithoutId) {
			Column annotation = field.getAnnotation(Column.class);
			if (annotation != null && !StringUtils.isEmpty(annotation.name())) {
				this.columnNamesWithoutId.add(annotation.name());
			} else {
				this.columnNamesWithoutId.add(camelToUnderline(field.getName()));
			}
		}

		//主键
		this.idField = MybatisUtils.getIdField(entityClass);
		this.idColumnName = MybatisUtils.camelToUnderline(idField.getName());

		//所有
		this.fields.addAll(this.fieldsWithoutId);
		this.fields.add(0, idField);
		this.columnNames.addAll(this.columnNames);
		this.columnNames.add(0, MybatisUtils.camelToUnderline(idField.getName()));
	}

	//TODO: insert
	/**
	 * 插入对象中非null的值到数据库
	 */
	public String insertSelective(T object) {

		StringBuilder nameBuilder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();

		for (Field field : fields) {
			if (MybatisUtils.getFieldValue(object, field) != null) {
				nameBuilder.append(",").append(MybatisUtils.camelToUnderline(field.getName()));
				valueBuilder.append(",#{").append(field.getName()).append("}");
			}
		}

		return "INSERT INTO " + tableName + "(" + nameBuilder.toString().replaceFirst(",", "") + ") " +
				"VALUES(" + valueBuilder.toString().replaceFirst(",", "") + ")";

	}

	/**
	 * 插入对象中的值到数据库，null值在数据库中会设置为NULL
	 */
	public String insert(T object) {

		StringBuilder nameBuilder = new StringBuilder();

		StringBuilder valueBuilder = new StringBuilder();
		for (Field field : fields) {
			nameBuilder.append(",").append(MybatisUtils.camelToUnderline(field.getName()));
			valueBuilder.append(",#{").append(field.getName()).append("}");

		}
		return "INSERT INTO " + tableName + "(" + nameBuilder.toString().replaceFirst(",", "") + ") " +
				"VALUES(" + valueBuilder.toString().replaceFirst(",", "") + ")";
	}

	//TODO: update
	/**
	 * 全更新 null值在 数据库中设置为null
	 */

	public String update(T entity) {

		ID id = (ID) MybatisUtils.getFieldValue(entity, idField);

		if (id == null) {
			throw new RuntimeException("修改时对象id不能为空");
		}

		StringBuilder sqlBuilder = new StringBuilder();
		for (Field field : fieldsWithoutId) {
			sqlBuilder.append("," + MybatisUtils.camelToUnderline(field.getName()) + "=#{" + field.getName() + "}");
		}

		String setValueSql = sqlBuilder.toString().replaceFirst(",", "");
		return "UPDATE " + tableName + " SET " + setValueSql + " WHERE " + idColumnName + "=#{id}";//set sql
	}

	//TODO: delete
	/**
	 * 根据id删除数据
	 */
	public String deleteById(ID id) {
		return "DELETE FROM " + tableName + " WHERE " + idColumnName + "=#{id}";
	}

	/**
	 * 删除所有数据
	 */
	public String delete() {
		return "DELETE FROM " + tableName;
	}

	/**
	 * 根据id列表批量删除数据
	 */
	public String deleteByIdList(Map<String, Object> map) {
		List<ID> list = (List<ID>) map.get("list");

		System.out.println(map);
		StringBuilder idsStr = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			idsStr.append(",#{list[").append(i).append("]}");
		}

		String sql = "DELETE FROM " + tableName + " WHERE   " + idColumnName + " IN  (" + idsStr.toString().replaceFirst(",", "") + ")";
		System.out.println(sql);
		return sql;
	}

	//TODO: FindById
	public String selectById(ID id){
		return "SELECT * FROM " + tableName + " WHERE " + idColumnName + "=#{id}";
	}

	//TODO: find list
	public String select(){
		return "SELECT * FROM " + tableName;
	}

	public String selectWhere(Map<String, Object> map) {
		Object[] params = (Object[]) map.get("param2");
		String sqlCondition = (String) map.get("param1");

		for (int i = 0; i < params.length; i++) {
			sqlCondition = sqlCondition.replaceFirst("\\?", "#{param2[" + i + "]}");
		}

		return "SELECT * FROM " + tableName + " WHERE " + sqlCondition;
	}

	public String selectByIds(Map<String, Object> map) {
		List<ID> list = (List<ID>) map.get("list");

		StringBuilder idsStr = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			idsStr.append(",#{list[").append(i).append("]}");
		}

		return "SELECT  * FROM " + tableName + " WHERE   " + idColumnName + " IN  (" + idsStr.toString().replaceFirst(",", "") + ")";
	}

	//TODO: count
	public String count() {
		return "SELECT COUNT(*) FROM " + tableName;
	}

	public String countWhere(Map<String, Object> map) {
		Object[] params = (Object[]) map.get("param2");
		String sqlCondition = (String) map.get("param1");

		for (int i = 0; i < params.length; i++) {
			sqlCondition = sqlCondition.replaceFirst("\\?", "#{param2[" + i + "]}");
		}

		return "SELECT count(*) FROM " + tableName + " WHERE " + sqlCondition;
	}

	//TODO: query
	public String query(int pageSize, int page){
		return "select top "+ pageSize +" * from (select ROW_NUMBER() over(Order by "+ idColumnName +") as rn, * from "+ tableName +")temTable where rn > "+ pageSize +"*("+ page +"-1) and rn <= "+ pageSize +"*"+ page;
	}

}
