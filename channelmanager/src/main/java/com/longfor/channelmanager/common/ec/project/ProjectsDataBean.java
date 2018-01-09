package com.longfor.channelmanager.common.ec.project;

import com.longfor.core.bean.BaseResponse;

import java.util.List;

/**
 * @author: tongzhenhua
 * @date: 2018/1/9
 * @function:
 */
public class ProjectsDataBean extends BaseResponse{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * areaId : D5FA76DF-DCC4-4939-ACE3-FEF74D1B5E62
         * projects : [{"projectId":"6072E9BF-851D-4527-BECF-073005D99E36","projectName":"长楹天街"},{"projectId":"EDFCED65-799C-460C-AEF2-16EA72895C55","projectName":"好望山"},{"projectId":"D50012F0-1DFD-4640-8C49-33A85833EDBD","projectName":"蔚澜香醍"},{"projectId":"CEE9533C-8B67-4EE7-BD89-70C1F4B44069","projectName":"白辛庄"},{"projectId":"20FB3264-9246-4226-A4E0-F83AC2D42F8C","projectName":"时代天街"},{"projectId":"10382AAB-1D14-E211-A4FF-18A90558DEC4","projectName":"双珑原著"},{"projectId":"EACA8D6D-1B5B-E311-8D79-0050568001F7","projectName":"滟澜新宸"},{"projectId":"6988C41F-EA7B-E311-8D79-0050568001F7","projectName":"长城源著"},{"projectId":"4846E6B3-7C97-E311-8D79-0050568001F7","projectName":"西宸原著"},{"projectId":"C23F3652-045B-4011-9A5F-FE683E8D5EA6","projectName":"老项目维修整改（不含商业）"},{"projectId":"F09305C2-2B0D-4A52-B0E0-2502BFC9E9A8","projectName":"西宸广场"},{"projectId":"C6618500-5F6B-4157-B5EE-AC8E7432A56C","projectName":"天璞"},{"projectId":"43E99F7A-4C2D-4210-915E-8C3C8A16E9F9","projectName":"门头沟"},{"projectId":"9326CA67-E3FA-4459-95A8-6C0E927E8189","projectName":"天琅"},{"projectId":"80B03281-B931-4838-AEAD-EE2BA11DF224","projectName":"景粼原著"},{"projectId":"10C8CCAF-90F6-40A1-9A98-1768A8F71E85","projectName":"商业升级改造项目"},{"projectId":"648CFC50-D407-44ED-8CAE-8EADDD65682C","projectName":"光合空间联合办公"},{"projectId":"F4CD8FD4-8E7D-4234-B32E-8CC8843E5B6D","projectName":"中关村环保科技园"},{"projectId":"965B3A2D-8D86-4259-8054-9D5ADD0DFD4E","projectName":"顺义幸福西街棚户区改造项目"},{"projectId":"98705F3E-B977-4F24-84BE-4E1B82CF0BD5","projectName":"房山长阳项目"},{"projectId":"7EFECD30-D9B2-436C-B00E-53DD1C6725B7","projectName":"冠寓酒仙桥冠捷项目"},{"projectId":"6EE2A38F-6705-46E1-8D5B-DAE36E2993A7","projectName":"房山良乡天街项目"},{"projectId":"1C3F8051-71E8-46BB-B05C-A38BB3F00928","projectName":"一展望京彩虹桥项目"},{"projectId":"011E7F1A-5A10-4FFF-BD63-CB6F56A1C054","projectName":"高碑店高铁新城住宅项目"},{"projectId":"575906F6-9F2C-44DF-8604-4EF1E96B41A2","projectName":"常营产业园"},{"projectId":"FB441C74-E8EE-40D7-9CA0-71ED4BC9B728","projectName":"冠寓工体方恒中心"},{"projectId":"96AACEB1-BD0C-475B-8E17-BF839DC01091","projectName":"冠寓北京通州区新华大街项目"},{"projectId":"9047345D-2B0B-4FC2-9DAE-6370C4FAA237","projectName":"冠寓博泰嘉华大厦"},{"projectId":"0D4330F6-17DB-4EB7-9539-49E609DB63D2","projectName":"南法信"},{"projectId":"E6CE1179-9C5D-4F18-A08A-7D5D65CC2900","projectName":"青龙湖"},{"projectId":"EA5FF177-9C22-4310-BF2E-9BCA75F90176","projectName":"孙河N地块"},{"projectId":"1175C5DE-7DFC-4026-9C91-47167F0DC81F","projectName":"仁和镇"},{"projectId":"CF5A9982-A518-493C-8C58-E03FE1B3F64B","projectName":"平谷金海湖"},{"projectId":"91D61350-40E7-4D90-BAA8-247DA7C8C90B","projectName":"新东坝"},{"projectId":"027ED136-FB77-425A-BF8A-C81D99FBBF34","projectName":"高丽营"},{"projectId":"E604DE8F-3D11-43B8-B56D-292AE7F5AD25","projectName":"潭柘寺"},{"projectId":"B9326A2A-3403-4CAD-BEC5-5E980AB1B4FF","projectName":"房山中海良乡"},{"projectId":"43DC9DC3-484A-4243-A5D4-CDE942B275C3","projectName":"太平庄"}]
         * areaName : 北京龙湖
         */

        private String areaId;
        private String areaName;
        private List<ProjectsBean> projects;

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public List<ProjectsBean> getProjects() {
            return projects;
        }

        public void setProjects(List<ProjectsBean> projects) {
            this.projects = projects;
        }

        public static class ProjectsBean {
            /**
             * projectId : 6072E9BF-851D-4527-BECF-073005D99E36
             * projectName : 长楹天街
             */

            private String projectId;
            private String projectName;

            public String getProjectId() {
                return projectId;
            }

            public void setProjectId(String projectId) {
                this.projectId = projectId;
            }

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
            }
        }
    }
}
