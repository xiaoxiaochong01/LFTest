package com.longfor.channelmanager.teamcampaign;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.longfor.channelmanager.R;
import com.longfor.channelmanager.R2;
import com.longfor.channelmanager.common.ec.Constant;
import com.longfor.channelmanager.common.ec.project.IProjectChange;
import com.longfor.channelmanager.common.ec.project.ProjectsDataBean;
import com.longfor.channelmanager.common.ec.project.popupwindow.ProjectsPopWindow;
import com.longfor.channelmanager.common.net.BaseSuccessListener;
import com.longfor.channelmanager.common.view.CommonHeadView;
import com.longfor.channelmanager.database.DatabaseManager;
import com.longfor.core.delegates.LongForDelegate;
import com.longfor.core.net.RestClient;
import com.longfor.core.net.callback.IError;
import com.longfor.core.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.longfor.channelmanager.teamcampaign.TeamCampaignConstant.REQUEST_LOCATION;

/**
 * @author: gaomei
 * @date: 2018/1/22
 * @function:
 */

public class TeamCampaignDelegate extends LongForDelegate implements IProjectChange {
    @BindView(R2.id.header_team_campaign)
    CommonHeadView mHeaderTeamCampaign;
    MapView mTeamCampaignMapView;
    private TextView mTvTitleRight;
    private ProjectsPopWindow mProjectWindow;
    public BaiduMap mBaiduMap;
    private List<Marker> mGroupingFightMarkerList = new ArrayList<>();
    private BaiduMap.OnMarkerClickListener mGroupingFightMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            for (int i = 0; i < mGroupingFightMarkerList.size(); i++) {
                if (marker == mGroupingFightMarkerList.get(i)) {
                    generateWindowInfo(i, marker);
                }
            }
            return true;
        }
    };
    public List<TeamCampaignBean.DataBean> mDateBeanList = new ArrayList<>();
    public String mProjectId;
    private boolean mIsPopShowing = false;
    public TeamCampaignComparator mTeamCampaignComparator;
    private LocationClient mLocClient;

    public static TeamCampaignDelegate getInstance(String leftStr) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.TITLE_LEFT_TEXT, leftStr);
        TeamCampaignDelegate delegate = new TeamCampaignDelegate();
        delegate.setArguments(bundle);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_team_campaign;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initHeader();
        initBaiduMap(rootView);
        initLocationConfig();
        checkLocationPermission();
        mTeamCampaignComparator = new TeamCampaignComparator();
        mProjectId = DatabaseManager.getProjectId();
        getGroupFightData(mProjectId);
    }

    private void initHeader() {
        mHeaderTeamCampaign.setLeftBackImageVisible(true);
        mHeaderTeamCampaign.setLeftMsg(getArguments().getString(Constant.TITLE_LEFT_TEXT));
        mHeaderTeamCampaign.setTitle(getString(R.string.channel_platform_team_campaign));
        mHeaderTeamCampaign.setRightTextViewVisible(true);
        mHeaderTeamCampaign.setRightTextViewText(DatabaseManager.getUserProfile().getProjectName());
        mTvTitleRight = (TextView) mHeaderTeamCampaign.findViewById(R.id.tv_head_common_right_text);
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_arrow_down_s);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mTvTitleRight.setCompoundDrawablePadding(5);
        mTvTitleRight.setCompoundDrawables(null, null, drawable, null);
        mHeaderTeamCampaign.setBottomLineVisible(true);
        mProjectWindow = new ProjectsPopWindow(getContext(), this);
        mHeaderTeamCampaign.setLeftLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().pop();
            }
        });
        mHeaderTeamCampaign.setRightLayoutOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProjectWindow.showPopWindow(mTvTitleRight);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mTeamCampaignMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mTeamCampaignMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocClient != null) {
            // 退出时销毁定位
            mLocClient.stop();
            mLocClient.unRegisterLocationListener(myListener);
            mLocClient = null;
        }
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mTeamCampaignMapView.onDestroy();
        mTeamCampaignMapView = null;
    }

    /**
     * 切换项目，重新设置数据
     * @param projectsBean
     */
    @Override
    public void changeSucess(ProjectsDataBean.DataBean.ProjectsBean projectsBean) {
        mTvTitleRight.setText(projectsBean.getProjectName());
        mProjectId = projectsBean.getProjectId();
        getGroupFightData(projectsBean.getProjectId());
    }

    /**
     * 初始化百度地图
     * @param rootView
     */
    private void initBaiduMap(View rootView) {
        mTeamCampaignMapView=rootView.findViewById(R.id.map_view_team_campaign);
        mBaiduMap = mTeamCampaignMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setCompassEnable(true);
        mBaiduMap.setTrafficEnabled(false);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, null));
        UiSettings uiSettings = mBaiduMap.getUiSettings();
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setOverlookingGesturesEnabled(false);
        //设置默认地图位置-天安门
        showMapArea(new LatLng(39.915119, 116.403963), 11);
    }

    /**
     * 定位初始化
     */
    private void initLocationConfig() {
        mLocClient = new LocationClient(getContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setIgnoreKillProcess(false);
        option.setScanSpan(0);
        mLocClient.setLocOption(option);
    }

    /**
     * 检查定位权限
     */
    private void checkLocationPermission() {
        String [] locationPermissionStr=new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        List<String> mRequestPermissionList=new ArrayList<>();
        for (int i = 0; i < locationPermissionStr.length; i++) {
            if (ActivityCompat.checkSelfPermission(getContext(),locationPermissionStr[i])!=PackageManager.PERMISSION_GRANTED){
                mRequestPermissionList.add(locationPermissionStr[i]);
            }
        }
        if (mRequestPermissionList.size()>0){
            ActivityCompat.requestPermissions(getActivity(), (String[]) mRequestPermissionList.toArray(new String[0]), REQUEST_LOCATION);
        }else {
            mLocClient.start();
        }
    }

    /**
     * 请求权限结果回调
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 请求结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    return;
                }
            }
            mLocClient.start();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 定位结果回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            try {
                if (mDateBeanList.size() > 0 || location == null) {
                } else {
                    setLocationData(location);
                    showMapArea(new LatLng(location.getLatitude(), location.getLongitude()), 19);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 设置定位数据
     *
     * @param location 定位点
     */
    private void setLocationData(BDLocation location) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(0)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        mBaiduMap.setMyLocationData(locData);
    }

    /**
     * 展示地图区域
     *
     * @param targetPosition 地图中心点
     */
    private void showMapArea(LatLng targetPosition, float zoom) {
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(targetPosition)
                .zoom(zoom)
                .build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
    }

    public MyLocationListener myListener = new MyLocationListener();
    /**
     * 获取某个项目的分组作战数据
     *
     * @param projectId 项目id
     */
    private void getGroupFightData(String projectId) {
        mDateBeanList.clear();
        mGroupingFightMarkerList.clear();
        mBaiduMap.clear();
        Map<String, String> map = new HashMap<>();
        map.put(Constant.EMPLOYEE_ID, DatabaseManager.getEmployeeId());
        map.put(Constant.PROJECT_ID,projectId);
        RestClient.builder().raw(map)
                .url(TeamCampaignConstant.URL_TEAM_CAMPAIGN)
                .success(new BaseSuccessListener() {
                    @Override
                    public void success(String response) {
                        handleNetData(response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showMessage(msg);
                    }
                })
                .loader(getContext())
                .build()
                .post();
    }

    private void handleNetData(String response) {
        TeamCampaignBean teamCampaignBean = JSON.parseObject(response, TeamCampaignBean.class);
        if (teamCampaignBean != null) {
            List<TeamCampaignBean.DataBean> dataBeanList = teamCampaignBean.getData();
            if (teamCampaignBean.getCode() == 0 && dataBeanList != null && dataBeanList.size() > 0) {
                for (int i = 0; i < dataBeanList.size(); i++) {
                    if (TextUtils.isEmpty(dataBeanList.get(i).getLatitude())
                            || TextUtils.isEmpty(dataBeanList.get(i).getLongitude())) {
                    } else {
                        mDateBeanList.add(dataBeanList.get(i));
                    }
                }
                if (mDateBeanList.size() > 0) {
                    showGroupAchievements();
                }
            }
        }
    }

    /**
     * 展示分组作战数据
     */
    private void showGroupAchievements() {
        Collections.sort(mDateBeanList, mTeamCampaignComparator);
        for (int i = 0; i < mDateBeanList.size(); i++) {
            generatePoint(i);
        }
        addPointToMap();
    }

    /**
     * 生成分组作战地点
     *
     * @param position 排名
     */
    private void generatePoint(int position) {
        String ranking = null;
        switch (mDateBeanList.get(position).getPosition()) {
            case 1:
                ranking = getResources().getString(R.string.first_ranking);
                generateRankingMarker(position, ranking);
                break;
            case 2:
                ranking = getResources().getString(R.string.second_ranking);
                generateRankingMarker(position, ranking);
                break;
            case 3:
                ranking = getResources().getString(R.string.third_ranking);
                generateRankingMarker(position, ranking);
                break;
            default:
                generateNormalMarker(position);
                break;
        }
    }

    /**
     * 生成排名的Marker
     *
     * @param position 排名
     * @param ranking  排名展示文本
     */
    private void generateRankingMarker(int position, String ranking) {
        View rankingGroupView = View.inflate(getContext(), R.layout.view_grouping_fight_ranking, null);
        TextView tvGroupRanking = rankingGroupView.findViewById(R.id.tv_group_ranking);
        TextView tvGroupName = rankingGroupView.findViewById(R.id.tv_group_name);
        tvGroupRanking.setText(ranking);
        setTeamNameText(position, tvGroupName);
        //构建Marker图标
        BitmapDescriptor rankingGroupBitmap = BitmapDescriptorFactory.fromView(rankingGroupView);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions rankingGroupMarkerOption = new MarkerOptions()
                .position(new LatLng(Double.valueOf(mDateBeanList.get(position).getLatitude()), Double.valueOf(mDateBeanList.get(position).getLongitude())))
                .icon(rankingGroupBitmap)
                .animateType(MarkerOptions.MarkerAnimateType.drop)
                .draggable(false);
        //在地图上添加Marker，并显示
        Marker rankingMarker = (Marker) mBaiduMap.addOverlay(rankingGroupMarkerOption);
        mGroupingFightMarkerList.add(rankingMarker);
    }

    /**
     * 生成普通Marker
     *
     * @param position 排名
     */
    private void generateNormalMarker(int position) {
        View normalGroupView = View.inflate(getContext(), R.layout.view_grouping_fight_normal, null);
        TextView tvGroup = normalGroupView.findViewById(R.id.tv_group);
        setTeamNameText(position, tvGroup);
        BitmapDescriptor normalGroupBitmap = BitmapDescriptorFactory.fromView(normalGroupView);
        OverlayOptions normalGroupMarkerOption = new MarkerOptions()
                .position(new LatLng(Double.valueOf(mDateBeanList.get(position).getLatitude()), Double.valueOf(mDateBeanList.get(position).getLongitude())))
                .icon(normalGroupBitmap)
                .animateType(MarkerOptions.MarkerAnimateType.drop)
                .draggable(false);
        Marker normalMarker = (Marker) mBaiduMap.addOverlay(normalGroupMarkerOption);
        mGroupingFightMarkerList.add(normalMarker);
    }

    /**
     * 生成Marker里的组名
     * 团队名称大于5要截取
     * 团队名称字数大于2与小于等于2的背景图片不一样
     *
     * @param position 排名
     * @param textView 组名
     */
    private void setTeamNameText(int position, TextView textView) {
        String teamName = "";
        if (mDateBeanList.get(position).getTeamName() != null) {//团队名称大于5要截取
            if (mDateBeanList.get(position).getTeamName().length() > 5) {
                teamName = mDateBeanList.get(position).getTeamName().substring(0, 5) + "...";
            } else {
                teamName = mDateBeanList.get(position).getTeamName();
            }
        }
        if (teamName.length() <= 2) {//团队名称字数大于2与小于等于2的背景图片不一样
            textView.setBackgroundResource(R.mipmap.ic_group_location_small);
        } else {
            textView.setBackgroundResource(R.mipmap.ic_group_location_big);
        }
        textView.setText(teamName);
    }

    /**
     * 地图上添加点
     */
    private void addPointToMap() {
        LatLng[] points = new LatLng[mDateBeanList.size()];
        for (int i = 0; i < mDateBeanList.size(); i++) {
            points[i] = new LatLng(Double.valueOf(mDateBeanList.get(i).getLatitude()), Double.valueOf(mDateBeanList.get(i).getLongitude()));
        }
        setZoom(points);
        mBaiduMap.setOnMarkerClickListener(mGroupingFightMarkerClickListener);
    }

    /**
     * 设置地图最大以及最小缩放级别，地图支持的最大最小级别分别为[3-21]
     */
    private void setZoom(LatLng[] points) {
        double maxLat = points[0].latitude;
        double minLat = points[0].latitude;
        double maxLng = points[0].longitude;
        double minLng = points[0].longitude;
        for (int i = 0; i < points.length; i++) {
            LatLng point = points[i];
            if (point.latitude > maxLat) {
                maxLat = point.latitude;
            }
            if (point.latitude < minLat) {
                minLat = point.latitude;
            }
            if (point.longitude > maxLng) {
                maxLng = point.longitude;
            }
            if (point.longitude < minLng) {
                minLng = point.longitude;
            }
        }
        LatLng center = new LatLng((maxLat + minLat) / 2, (maxLng + minLng) / 2);
        int zoom = getZoom(maxLat, minLat, maxLng, minLng);
        showMapArea(center, zoom);
    }

    /**
     * 根据经纬度的最大最小值获取地图展示精度
     *
     * @param maxLat 最大纬度
     * @param minLat 最小纬度
     * @param maxLng 最大精度
     * @param minLng 最小精度
     * @return
     */
    private int getZoom(double maxLat, double minLat, double maxLng, double minLng) {
        double[] zooms = new double[]{5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000};
        LatLng maxLl = new LatLng(maxLat, maxLng);
        LatLng minLl = new LatLng(minLat, minLng);
        double distance = DistanceUtil.getDistance(maxLl, minLl);
        for (int i = 0; i < zooms.length; i++) {
            if (zooms[i] - distance > 0) {
                return (21 - i + 2) > 20 ? 20 : (21 - i + 2);//缩放精度最大返回20，对应10m的比例尺。
            }
        }
        return 7;
    }

    /**
     * 生成地图弹框
     *
     * @param position 排名
     * @param marker   弹框对应的Marker
     */
    private void generateWindowInfo(int position, Marker marker) {
        View infoWindowView = View.inflate(getContext(), R.layout.view_grouping_fight_info_window, null);
        TextView textView = infoWindowView.findViewById(R.id.tv_headman);
        textView.setText(getString(R.string.headman_and_colon) + mDateBeanList.get(position).getTeamLeader());
        RecyclerView rvGroupAchievement = infoWindowView.findViewById(R.id.rv_group_achievement);
        LinearLayoutManager layoutManager = new LinearLayoutManager(infoWindowView.getContext(), LinearLayoutManager.VERTICAL, false);
        rvGroupAchievement.setLayoutManager(layoutManager);
        TeamCampaignWindowInfoRvAdapter adapter = new TeamCampaignWindowInfoRvAdapter(mDateBeanList.get(position).getMissions());
        rvGroupAchievement.setAdapter(adapter);
        LatLng latLng = marker.getPosition();
        final InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(infoWindowView), latLng, infoWindowView.getHeight(), new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                mBaiduMap.hideInfoWindow();
            }
        });
        mBaiduMap.showInfoWindow(mInfoWindow);
    }
}
