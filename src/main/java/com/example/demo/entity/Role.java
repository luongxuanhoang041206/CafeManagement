package com.example.demo.entity;

import java.util.Set;

public enum Role  {
	ROLE_ADMIN(Set.of(
	        Permission.PRODUCT_VIEW,
	        Permission.PRODUCT_CREATE,
	        Permission.PRODUCT_UPDATE,
	        Permission.PRODUCT_DELETE,
	        
	        Permission.EMPLOYEE_VIEW,
	        Permission.EMPLOYEE_CREATE,
	        Permission.EMPLOYEE_UPDATE,
	        Permission.EMPLOYEE_DELETE,
	        
	        Permission.ORDER_VIEW,
	        Permission.ORDER_CREATE,
	        Permission.ORDER_CANCEL,
	        
	        Permission.USER_VIEW,
	        Permission.USER_CREATE,
	        Permission.USER_DELETE
	        
	    )),

	    ROLE_MANAGER(Set.of(
	        Permission.PRODUCT_VIEW,
	        Permission.PRODUCT_CREATE,
	        Permission.PRODUCT_UPDATE,
	        
	        Permission.EMPLOYEE_CREATE,
	        Permission.EMPLOYEE_UPDATE,
	        Permission.EMPLOYEE_DELETE,
	        
	        Permission.ORDER_VIEW,
	        Permission.ORDER_CREATE,
	        Permission.ORDER_CANCEL
	    )),

	    ROLE_STAFF(Set.of(
	        Permission.PRODUCT_VIEW,
	        
	        Permission.ORDER_VIEW,
	        Permission.ORDER_CREATE
	    ));

	    private final Set<Permission> permissions;

	    Role(Set<Permission> permissions) {
	        this.permissions = permissions;
	    }

	    public Set<Permission> getPermissions() {
	        return permissions;
	    }
}