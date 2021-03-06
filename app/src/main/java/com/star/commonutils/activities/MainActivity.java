package com.star.commonutils.activities;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.star.common_utils.utils.AppUtil;
import com.star.common_utils.utils.LogUtil;
import com.star.common_utils.widget.dialogfragment.BottomPopListDialogFragment;
import com.star.common_utils.widget.dialogfragment.DialogFragmentDismissListener;
import com.star.commonutils.R;
import com.star.commonutils.fragments.MyDialogFragmentFullScreen;
import com.star.commonutils.fragments.MyDialogFragmentUnFullScreen;
import com.star.commonutils.retention_defs.DispatcherType;
import com.star.xpermission.OnPermissionCallback;
import com.star.xpermission.PermissionSparseArray;
import com.star.xpermission.XPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener, DialogFragmentDismissListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_translucent_status).setOnClickListener(this);
        findViewById(R.id.btn_wheel_banner).setOnClickListener(this);
        findViewById(R.id.edit_img).setOnClickListener(this);
        findViewById(R.id.swipe_delete).setOnClickListener(this);
        findViewById(R.id.custom_view).setOnClickListener(this);
        findViewById(R.id.line_pager_title).setOnClickListener(this);
        findViewById(R.id.app_install).setOnClickListener(this);
        findViewById(R.id.dialogFragmentFullScreen).setOnClickListener(this);
        findViewById(R.id.dialogFragmentUnFullScreen).setOnClickListener(this);
        findViewById(R.id.bottom_pop_dialog_fragment).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_translucent_status:
                startActivity(TranslucentStatusBarActivity.makeIntent(this));
                break;
            case R.id.btn_wheel_banner:
                startActivity(BannersActivity.makeIntent(this));
                break;
            case R.id.edit_img:
                startActivity(DispatcherActivity.makeIntent(this, DispatcherType.DISPATCH_EDIT_IMAGE));
                break;
            case R.id.swipe_delete:
                startActivity(DispatcherActivity.makeIntent(this, DispatcherType.DISPATCH_SWIPE_DELETE));
                break;
            case R.id.custom_view:
                startActivity(DispatcherActivity.makeIntent(this, DispatcherType.DISPATCH_CUSTOM_VIEW));
                break;
            case R.id.line_pager_title:
                startActivity(DispatcherActivity.makeIntent(this, DispatcherType.DISPATCH_LINE_PAGER_TITLE));
                break;
            case R.id.app_install:
                XPermission.permissionRequest(this, PermissionSparseArray.PERMISSION_STORAGE, new OnPermissionCallback() {
                    @Override
                    public void onPermissionGranted(int reqCode) {
                        AppUtil.installApk(MainActivity.this, new File(Environment.getExternalStorageDirectory(), "test.apk"), "com.star.commonutils.fileprovider");
                    }

                    @Override
                    public void onPermissionDenied(String deniedPermission, int reqCode) {
                        XPermission.showTipDialog(MainActivity.this, "请授予权限", "该权限读取sd卡内容");
                    }

                    @Override
                    public void shouldShowRequestPermissionTip(String requestPermissionRationale, int reqCode) {
                        XPermission.showTipDialog(MainActivity.this, "请授予权限", "该权限读取sd卡内容");
                    }
                });
                break;
            case R.id.dialogFragmentFullScreen:
                MyDialogFragmentFullScreen dialogFragmentFullScreen = new MyDialogFragmentFullScreen();
                dialogFragmentFullScreen.show(getSupportFragmentManager());
                dialogFragmentFullScreen.setDialogFragmentDismissListener(this);
                break;
            case R.id.dialogFragmentUnFullScreen:
                MyDialogFragmentUnFullScreen dialogFragmentUnFullScreen = new MyDialogFragmentUnFullScreen();
                dialogFragmentUnFullScreen.show(getSupportFragmentManager());
                dialogFragmentUnFullScreen.setDialogFragmentDismissListener(this);
                break;
            case R.id.bottom_pop_dialog_fragment:
                BottomPopListDialogFragment bottomPopListDialogFragment = new BottomPopListDialogFragment();
                List<BottomPopListDialogFragment.BottomPopItem> items = new ArrayList<>();
                items.add(new BottomPopListDialogFragment.BottomPopItem("time", "服务时间：周一至周日 9:00-18:00", "#999999", AppUtil.dp2px(this, 11), AppUtil.dp2px(this, 38)));
                items.add(new BottomPopListDialogFragment.BottomPopItem("call", "拨打：0000-000-000"));
                items.add(new BottomPopListDialogFragment.BottomPopItem("chat", "在线客服"));
                bottomPopListDialogFragment.setBottomPopItems(items);
                bottomPopListDialogFragment.setPopListDialogItemClickListener(new BottomPopListDialogFragment.BottomPopListDialogItemClickListener() {
                    @Override
                    public void onItemClick(BottomPopListDialogFragment.BottomPopItem bottomPopItem) {
                        switch (bottomPopItem.id) {
                            case "call":
                                break;
                            case "chat":
                                break;
                        }
                    }
                });
                bottomPopListDialogFragment.show(getSupportFragmentManager());
                break;
        }
    }

    @Override
    protected boolean enableSwipeBack() {
        return false;
    }

    @Override
    public void onDialogFragmentDismiss() {
        LogUtil.d("onDialogFragmentDismiss");
    }
}
