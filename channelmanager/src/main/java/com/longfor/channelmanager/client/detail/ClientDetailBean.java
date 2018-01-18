package com.longfor.channelmanager.client.detail;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/16
 * @function:
 */
public class ClientDetailBean extends BaseResponse {

    /**
     * data : {"follows":[{"employeeName":"测试好好","empId":97190,"roleName":"","followDetails":[{"followContent":"哈喽啥快奖励个咯啦咯啦咯啦咯啦咯","followTime":1515138323000,"name":"外呼陌电1录"}]}],"canFollow":0,"customer":{"intentTrade":"","isStranger":"","visitingCount":"","phoneNum":"15827006714","mainResistance":"","relatedHouseBuyer":"","housePurpose":"","hasRelated":0,"followedByAgent":0,"childStatus":"","customerId":949185098270511104,"budget":"","createDate":"2018-01-05","attentionPoint":"","userSex":"1","requirements":"哈喽啥快奖励个咯啦咯啦咯啦咯啦咯","workPosition":"","lastFollowType":"外呼陌电1录","customerGroup":"","lastFollowPerson":"测试好好","ageStage":"","marriageStatus":"","neverContact":"","intent":"F","customerName":"号楼哈only用","customerLevel":"1级","tags":["1"],"lastFollowTime":"2018-01-05","competitiveBuilding":"","familyStructure":"","isLongforCustomer":"","workIndustry":"","schoolRequirement":"","followName":"测试好好","propertyConsultant":"","firstVisitDate":""}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * follows : [{"employeeName":"测试好好","empId":97190,"roleName":"","followDetails":[{"followContent":"哈喽啥快奖励个咯啦咯啦咯啦咯啦咯","followTime":1515138323000,"name":"外呼陌电1录"}]}]
         * canFollow : 0
         * customer : {"intentTrade":"","isStranger":"","visitingCount":"","phoneNum":"15827006714","mainResistance":"","relatedHouseBuyer":"","housePurpose":"","hasRelated":0,"followedByAgent":0,"childStatus":"","customerId":949185098270511104,"budget":"","createDate":"2018-01-05","attentionPoint":"","userSex":"1","requirements":"哈喽啥快奖励个咯啦咯啦咯啦咯啦咯","workPosition":"","lastFollowType":"外呼陌电1录","customerGroup":"","lastFollowPerson":"测试好好","ageStage":"","marriageStatus":"","neverContact":"","intent":"F","customerName":"号楼哈only用","customerLevel":"1级","tags":["1"],"lastFollowTime":"2018-01-05","competitiveBuilding":"","familyStructure":"","isLongforCustomer":"","workIndustry":"","schoolRequirement":"","followName":"测试好好","propertyConsultant":"","firstVisitDate":""}
         */

        private int canFollow;
        private CustomerBean customer;
        private List<FollowsBean> follows;

        public int getCanFollow() {
            return canFollow;
        }

        public void setCanFollow(int canFollow) {
            this.canFollow = canFollow;
        }

        public CustomerBean getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerBean customer) {
            this.customer = customer;
        }

        public List<FollowsBean> getFollows() {
            return follows;
        }

        public void setFollows(List<FollowsBean> follows) {
            this.follows = follows;
        }

        public static class CustomerBean {
            /**
             * intentTrade :
             * isStranger :
             * visitingCount :
             * phoneNum : 15827006714
             * mainResistance :
             * relatedHouseBuyer :
             * housePurpose :
             * hasRelated : 0
             * followedByAgent : 0
             * childStatus :
             * customerId : 949185098270511104
             * budget :
             * createDate : 2018-01-05
             * attentionPoint :
             * userSex : 1
             * requirements : 哈喽啥快奖励个咯啦咯啦咯啦咯啦咯
             * workPosition :
             * lastFollowType : 外呼陌电1录
             * customerGroup :
             * lastFollowPerson : 测试好好
             * ageStage :
             * marriageStatus :
             * neverContact :
             * intent : F
             * customerName : 号楼哈only用
             * customerLevel : 1级
             * tags : ["1"]
             * lastFollowTime : 2018-01-05
             * competitiveBuilding :
             * familyStructure :
             * isLongforCustomer :
             * workIndustry :
             * schoolRequirement :
             * followName : 测试好好
             * propertyConsultant :
             * firstVisitDate :
             */

            private String intentTrade;
            private String isStranger;
            private String visitingCount;
            private String phoneNum;
            private String mainResistance;
            private String relatedHouseBuyer;
            private String housePurpose;
            private int hasRelated;
            private int followedByAgent;
            private String childStatus;
            private long customerId;
            private String budget;
            private String createDate;
            private String attentionPoint;
            private String userSex;
            private String requirements;
            private String workPosition;
            private String lastFollowType;
            private String customerGroup;
            private String lastFollowPerson;
            private String ageStage;
            private String marriageStatus;
            private String neverContact;
            private String intent;
            private String customerName;
            private String customerLevel;
            private String lastFollowTime;
            private String competitiveBuilding;
            private String familyStructure;
            private String isLongforCustomer;
            private String workIndustry;
            private String schoolRequirement;
            private String followName;
            private String propertyConsultant;
            private String firstVisitDate;
            private List<String> tags;
            private String area;

            public void setArea(String area) {
                this.area = area;
            }

            public String getArea() {
                return area;
            }

            public String getIntentTrade() {
                return intentTrade;
            }

            public void setIntentTrade(String intentTrade) {
                this.intentTrade = intentTrade;
            }

            public String getIsStranger() {
                return isStranger;
            }

            public void setIsStranger(String isStranger) {
                this.isStranger = isStranger;
            }

            public String getVisitingCount() {
                return visitingCount;
            }

            public void setVisitingCount(String visitingCount) {
                this.visitingCount = visitingCount;
            }

            public String getPhoneNum() {
                return phoneNum;
            }

            public void setPhoneNum(String phoneNum) {
                this.phoneNum = phoneNum;
            }

            public String getMainResistance() {
                return mainResistance;
            }

            public void setMainResistance(String mainResistance) {
                this.mainResistance = mainResistance;
            }

            public String getRelatedHouseBuyer() {
                return relatedHouseBuyer;
            }

            public void setRelatedHouseBuyer(String relatedHouseBuyer) {
                this.relatedHouseBuyer = relatedHouseBuyer;
            }

            public String getHousePurpose() {
                return housePurpose;
            }

            public void setHousePurpose(String housePurpose) {
                this.housePurpose = housePurpose;
            }

            public int getHasRelated() {
                return hasRelated;
            }

            public void setHasRelated(int hasRelated) {
                this.hasRelated = hasRelated;
            }

            public int getFollowedByAgent() {
                return followedByAgent;
            }

            public void setFollowedByAgent(int followedByAgent) {
                this.followedByAgent = followedByAgent;
            }

            public String getChildStatus() {
                return childStatus;
            }

            public void setChildStatus(String childStatus) {
                this.childStatus = childStatus;
            }

            public long getCustomerId() {
                return customerId;
            }

            public void setCustomerId(long customerId) {
                this.customerId = customerId;
            }

            public String getBudget() {
                return budget;
            }

            public void setBudget(String budget) {
                this.budget = budget;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getAttentionPoint() {
                return attentionPoint;
            }

            public void setAttentionPoint(String attentionPoint) {
                this.attentionPoint = attentionPoint;
            }

            public String getUserSex() {
                return userSex;
            }

            public void setUserSex(String userSex) {
                this.userSex = userSex;
            }

            public String getRequirements() {
                return requirements;
            }

            public void setRequirements(String requirements) {
                this.requirements = requirements;
            }

            public String getWorkPosition() {
                return workPosition;
            }

            public void setWorkPosition(String workPosition) {
                this.workPosition = workPosition;
            }

            public String getLastFollowType() {
                return lastFollowType;
            }

            public void setLastFollowType(String lastFollowType) {
                this.lastFollowType = lastFollowType;
            }

            public String getCustomerGroup() {
                return customerGroup;
            }

            public void setCustomerGroup(String customerGroup) {
                this.customerGroup = customerGroup;
            }

            public String getLastFollowPerson() {
                return lastFollowPerson;
            }

            public void setLastFollowPerson(String lastFollowPerson) {
                this.lastFollowPerson = lastFollowPerson;
            }

            public String getAgeStage() {
                return ageStage;
            }

            public void setAgeStage(String ageStage) {
                this.ageStage = ageStage;
            }

            public String getMarriageStatus() {
                return marriageStatus;
            }

            public void setMarriageStatus(String marriageStatus) {
                this.marriageStatus = marriageStatus;
            }

            public String getNeverContact() {
                return neverContact;
            }

            public void setNeverContact(String neverContact) {
                this.neverContact = neverContact;
            }

            public String getIntent() {
                return intent;
            }

            public void setIntent(String intent) {
                this.intent = intent;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerLevel() {
                return customerLevel;
            }

            public void setCustomerLevel(String customerLevel) {
                this.customerLevel = customerLevel;
            }

            public String getLastFollowTime() {
                return lastFollowTime;
            }

            public void setLastFollowTime(String lastFollowTime) {
                this.lastFollowTime = lastFollowTime;
            }

            public String getCompetitiveBuilding() {
                return competitiveBuilding;
            }

            public void setCompetitiveBuilding(String competitiveBuilding) {
                this.competitiveBuilding = competitiveBuilding;
            }

            public String getFamilyStructure() {
                return familyStructure;
            }

            public void setFamilyStructure(String familyStructure) {
                this.familyStructure = familyStructure;
            }

            public String getIsLongforCustomer() {
                return isLongforCustomer;
            }

            public void setIsLongforCustomer(String isLongforCustomer) {
                this.isLongforCustomer = isLongforCustomer;
            }

            public String getWorkIndustry() {
                return workIndustry;
            }

            public void setWorkIndustry(String workIndustry) {
                this.workIndustry = workIndustry;
            }

            public String getSchoolRequirement() {
                return schoolRequirement;
            }

            public void setSchoolRequirement(String schoolRequirement) {
                this.schoolRequirement = schoolRequirement;
            }

            public String getFollowName() {
                return followName;
            }

            public void setFollowName(String followName) {
                this.followName = followName;
            }

            public String getPropertyConsultant() {
                return propertyConsultant;
            }

            public void setPropertyConsultant(String propertyConsultant) {
                this.propertyConsultant = propertyConsultant;
            }

            public String getFirstVisitDate() {
                return firstVisitDate;
            }

            public void setFirstVisitDate(String firstVisitDate) {
                this.firstVisitDate = firstVisitDate;
            }

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
            }
        }

        public static class FollowsBean {
            /**
             * employeeName : 测试好好
             * empId : 97190
             * roleName :
             * followDetails : [{"followContent":"哈喽啥快奖励个咯啦咯啦咯啦咯啦咯","followTime":1515138323000,"name":"外呼陌电1录"}]
             */

            private String employeeName;
            private int empId;
            private String roleName;
            private List<FollowDetailsBean> followDetails;

            public String getEmployeeName() {
                return employeeName;
            }

            public void setEmployeeName(String employeeName) {
                this.employeeName = employeeName;
            }

            public int getEmpId() {
                return empId;
            }

            public void setEmpId(int empId) {
                this.empId = empId;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public List<FollowDetailsBean> getFollowDetails() {
                return followDetails;
            }

            public void setFollowDetails(List<FollowDetailsBean> followDetails) {
                this.followDetails = followDetails;
            }

            public static class FollowDetailsBean {
                /**
                 * followContent : 哈喽啥快奖励个咯啦咯啦咯啦咯啦咯
                 * followTime : 1515138323000
                 * name : 外呼陌电1录
                 */

                private String followContent;
                private long followTime;
                private String name;

                public String getFollowContent() {
                    return followContent;
                }

                public void setFollowContent(String followContent) {
                    this.followContent = followContent;
                }

                public long getFollowTime() {
                    return followTime;
                }

                public void setFollowTime(long followTime) {
                    this.followTime = followTime;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
