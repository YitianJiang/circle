package com.acircle.circle.model;

import java.util.ArrayList;
import java.util.List;

public class FollowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FollowExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andFromUserIdIsNull() {
            addCriterion("from_user_id is null");
            return (Criteria) this;
        }

        public Criteria andFromUserIdIsNotNull() {
            addCriterion("from_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromUserIdEqualTo(Long value) {
            addCriterion("from_user_id =", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotEqualTo(Long value) {
            addCriterion("from_user_id <>", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdGreaterThan(Long value) {
            addCriterion("from_user_id >", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_user_id >=", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdLessThan(Long value) {
            addCriterion("from_user_id <", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdLessThanOrEqualTo(Long value) {
            addCriterion("from_user_id <=", value, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdIn(List<Long> values) {
            addCriterion("from_user_id in", values, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotIn(List<Long> values) {
            addCriterion("from_user_id not in", values, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdBetween(Long value1, Long value2) {
            addCriterion("from_user_id between", value1, value2, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andFromUserIdNotBetween(Long value1, Long value2) {
            addCriterion("from_user_id not between", value1, value2, "fromUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdIsNull() {
            addCriterion("to_user_id is null");
            return (Criteria) this;
        }

        public Criteria andToUserIdIsNotNull() {
            addCriterion("to_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andToUserIdEqualTo(Long value) {
            addCriterion("to_user_id =", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdNotEqualTo(Long value) {
            addCriterion("to_user_id <>", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdGreaterThan(Long value) {
            addCriterion("to_user_id >", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("to_user_id >=", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdLessThan(Long value) {
            addCriterion("to_user_id <", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdLessThanOrEqualTo(Long value) {
            addCriterion("to_user_id <=", value, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdIn(List<Long> values) {
            addCriterion("to_user_id in", values, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdNotIn(List<Long> values) {
            addCriterion("to_user_id not in", values, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdBetween(Long value1, Long value2) {
            addCriterion("to_user_id between", value1, value2, "toUserId");
            return (Criteria) this;
        }

        public Criteria andToUserIdNotBetween(Long value1, Long value2) {
            addCriterion("to_user_id not between", value1, value2, "toUserId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}