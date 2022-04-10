package com.cetuer.smartparkinglot.ui.page.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.cetuer.smartparkinglot.App;
import com.cetuer.smartparkinglot.BR;
import com.cetuer.smartparkinglot.R;
import com.cetuer.smartparkinglot.data.bean.Member;
import com.cetuer.smartparkinglot.databinding.FragmentMineBinding;
import com.cetuer.smartparkinglot.domain.config.DataBindingConfig;
import com.cetuer.smartparkinglot.domain.message.SharedViewModel;
import com.cetuer.smartparkinglot.ui.page.BaseFragment;
import com.cetuer.smartparkinglot.utils.DialogUtils;
import com.cetuer.smartparkinglot.utils.SPUtils;
import com.cetuer.smartparkinglot.utils.ToastUtils;

import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MineFragment extends BaseFragment<FragmentMineBinding> {

    private MineViewModel mState;
    private SharedViewModel mEvent;
    //新密码
    private String newPassword;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(MineViewModel.class);
        mEvent = App.getInstance().getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_mine, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //请求用户信息
        mEvent.memberRequest.requestMemberInfo();
        //得到用户信息后渲染界面
        mEvent.memberRequest.getMemberInfo().observe(getViewLifecycleOwner(), member -> {
            //模糊背景
            Glide.with(this.mActivity).load(TextUtils.isEmpty(member.getAvatar()) ? R.drawable.default_avatar : member.getAvatar())
                    .bitmapTransform(new BlurTransformation(this.mActivity, 25), new CenterCrop(this.mActivity))
                    .into(mBinding.hBack);
            //头像
            Glide.with(this.mActivity).load(TextUtils.isEmpty(member.getAvatar()) ? R.drawable.default_avatar : member.getAvatar())
                    .bitmapTransform(new CropCircleTransformation(this.mActivity))
                    .into(mBinding.avatar);
            //性别
            Glide.with(this.mActivity).load((member.getGender() != null && member.getGender() == 2) ? R.drawable.gender_female : R.drawable.gender_male)
                    .into(mBinding.gender);
            //昵称
            mState.nickname.set(TextUtils.isEmpty(member.getNickname()) ? "暂无" : member.getNickname());
            String phone = member.getPhone();
            if (TextUtils.isEmpty(phone)) {
                phone = "暂无";
            } else {
                String prefix = phone.substring(0, 3);
                String suffix = phone.substring(7, 11);
                phone = prefix + "****" + suffix;
            }
            //手机号
            mState.phone.set(phone);
        });
        //修改个人信息
        mBinding.editInfo.setOnClickListener(v -> new MaterialDialog.Builder(MineFragment.this.mActivity)
                .title("修改个人信息")
                .customView(R.layout.layout_edit_info, true)
                .positiveText("确定")
                .onPositive((dialog, which) -> {
                    TextView nicknameText = dialog.getCustomView().findViewById(R.id.nickname);
                    TextView phoneText = dialog.getCustomView().findViewById(R.id.phone);
                    RadioGroup genderGroup = dialog.getCustomView().findViewById(R.id.gender_group);
                    String pattern = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57]|19[89]|166)[0-9]{8}";
                    //非手机号
                    if(!Pattern.compile(pattern).matcher(phoneText.getText()).matches()) {
                        ToastUtils.showShortToast(MineFragment.this.mActivity, "请输入正确的手机号");
                        return;
                    }
                    int gender = 0;
                    int checkedButtonId = genderGroup.getCheckedRadioButtonId();
                    if (checkedButtonId == R.id.radio_male) {
                        gender = 1;
                    } else if (checkedButtonId == R.id.radio_female) {
                        gender = 2;
                    }

                    mEvent.memberRequest.requestUpdateInfo(new Member(nicknameText.getText().toString(), phoneText.getText().toString(), gender));
                })
                .negativeText("取消").show());
        mEvent.memberRequest.getUserInfo().observe(getViewLifecycleOwner(), unused -> {
            ToastUtils.showShortToast(MineFragment.this.mActivity, "修改个人信息成功");
            mEvent.memberRequest.requestMemberInfo();
        });
        //停车记录
        mBinding.carHistory.setOnClickListener(v -> DialogUtils.showBasicDialogNoCancel(MineFragment.this.mActivity, "提示", "开发中，敬请期待~~~").show());
        //客服中心
        mBinding.kefu.setOnClickListener(v -> DialogUtils.showBasicDialogNoCancel(MineFragment.this.mActivity, "提示", "客服正在忙~").show());
        //关于我们
        mBinding.aboutUs.setOnClickListener(v -> DialogUtils.showBasicDialogNoCancel(MineFragment.this.mActivity, "提示", "安庆师范大学2020级计算机科学与技术（专升本）毕设项目\n\n 作者：董伊凡").show());
        //设置
        mBinding.setting.setOnClickListener(v -> DialogUtils.showBasicDialogNoCancel(MineFragment.this.mActivity, "提示", "开发中，敬请期待~~~").show());
        //退出登录
        mBinding.logout.setOnClickListener(v -> mEvent.memberRequest.requestLogout());
        //退出成功
        mEvent.memberRequest.getLogout().observe(getViewLifecycleOwner(), unused -> {
            SPUtils.getInstance().remove("token");
            ToastUtils.showShortToast(MineFragment.this.mActivity, "退出成功");
        });
        //修改密码
        mBinding.resetPwd.setOnClickListener(v -> new MaterialDialog.Builder(MineFragment.this.mActivity)
                .title("修改密码")
                .customView(R.layout.layout_edit_pwd, true)
                .positiveText("确定")
                .onPositive((dialog, which) -> {
                    EditText oldPwdEdit = dialog.getCustomView().findViewById(R.id.old_pwd);
                    EditText newPwdEdit = dialog.getCustomView().findViewById(R.id.new_pwd);
                    String oldPwd = oldPwdEdit.getText().toString();
                    String newPwd = newPwdEdit.getText().toString();

                    if (oldPwd.length() < 5 || oldPwd.length() > 20 || newPwd.length() < 5 || newPwd.length() > 20) {
                        ToastUtils.showShortToast(MineFragment.this.mActivity, "密码长度在5-20之间");
                        return;
                    }
                    newPassword = newPwd;
                    //检查旧密码是否正确
                    mEvent.memberRequest.requestMatchPwd(oldPwd);
                })
                .negativeText("取消").show());
        //旧密码匹配
        mEvent.memberRequest.getIsMatchPwd().observe(getViewLifecycleOwner(), isCorrect -> {
            if (isCorrect) {
                //正确则请求重置密码
                mEvent.memberRequest.requestResetPwd(newPassword);
                return;
            }
            ToastUtils.showShortToast(MineFragment.this.mActivity, "修改失败，密码错误！");
        });
        //重置密码成功
        mEvent.memberRequest.getResetPwd().observe(getViewLifecycleOwner(), unused -> ToastUtils.showShortToast(MineFragment.this.mActivity, "修改密码成功"));
    }
}