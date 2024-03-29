// Generated by view binder compiler. Do not edit!
package com.krashkrosh748199.shoption.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputLayout;
import com.krashkrosh748199.shoption.R;
import com.krashkrosh748199.shoption.utils.MSPButton;
import com.krashkrosh748199.shoption.utils.MSPEditText;
import com.krashkrosh748199.shoption.utils.MSPTextView;
import com.krashkrosh748199.shoption.utils.MSPTextViewBold;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MSPButton btnRegister;

  @NonNull
  public final AppCompatCheckBox cbTermsAndCondition;

  @NonNull
  public final MSPEditText etConfirmPassword;

  @NonNull
  public final MSPEditText etEmail;

  @NonNull
  public final MSPEditText etFirstName;

  @NonNull
  public final MSPEditText etLastName;

  @NonNull
  public final MSPEditText etPassword;

  @NonNull
  public final LinearLayout llTermsAndCondition;

  @NonNull
  public final TextInputLayout tilConfirmPassword;

  @NonNull
  public final TextInputLayout tilEmail;

  @NonNull
  public final TextInputLayout tilFirstName;

  @NonNull
  public final TextInputLayout tilLastName;

  @NonNull
  public final TextInputLayout tilPassword;

  @NonNull
  public final Toolbar toolbarRegisterActivity;

  @NonNull
  public final MSPTextView tvAlreadyHaveAnAccount;

  @NonNull
  public final MSPTextViewBold tvLogin;

  @NonNull
  public final MSPTextView tvTermsCondition;

  @NonNull
  public final MSPTextViewBold tvTitle;

  private ActivityRegisterBinding(@NonNull ConstraintLayout rootView,
      @NonNull MSPButton btnRegister, @NonNull AppCompatCheckBox cbTermsAndCondition,
      @NonNull MSPEditText etConfirmPassword, @NonNull MSPEditText etEmail,
      @NonNull MSPEditText etFirstName, @NonNull MSPEditText etLastName,
      @NonNull MSPEditText etPassword, @NonNull LinearLayout llTermsAndCondition,
      @NonNull TextInputLayout tilConfirmPassword, @NonNull TextInputLayout tilEmail,
      @NonNull TextInputLayout tilFirstName, @NonNull TextInputLayout tilLastName,
      @NonNull TextInputLayout tilPassword, @NonNull Toolbar toolbarRegisterActivity,
      @NonNull MSPTextView tvAlreadyHaveAnAccount, @NonNull MSPTextViewBold tvLogin,
      @NonNull MSPTextView tvTermsCondition, @NonNull MSPTextViewBold tvTitle) {
    this.rootView = rootView;
    this.btnRegister = btnRegister;
    this.cbTermsAndCondition = cbTermsAndCondition;
    this.etConfirmPassword = etConfirmPassword;
    this.etEmail = etEmail;
    this.etFirstName = etFirstName;
    this.etLastName = etLastName;
    this.etPassword = etPassword;
    this.llTermsAndCondition = llTermsAndCondition;
    this.tilConfirmPassword = tilConfirmPassword;
    this.tilEmail = tilEmail;
    this.tilFirstName = tilFirstName;
    this.tilLastName = tilLastName;
    this.tilPassword = tilPassword;
    this.toolbarRegisterActivity = toolbarRegisterActivity;
    this.tvAlreadyHaveAnAccount = tvAlreadyHaveAnAccount;
    this.tvLogin = tvLogin;
    this.tvTermsCondition = tvTermsCondition;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_register;
      MSPButton btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.cb_terms_and_condition;
      AppCompatCheckBox cbTermsAndCondition = ViewBindings.findChildViewById(rootView, id);
      if (cbTermsAndCondition == null) {
        break missingId;
      }

      id = R.id.et_confirm_password;
      MSPEditText etConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (etConfirmPassword == null) {
        break missingId;
      }

      id = R.id.et_email;
      MSPEditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.et_first_name;
      MSPEditText etFirstName = ViewBindings.findChildViewById(rootView, id);
      if (etFirstName == null) {
        break missingId;
      }

      id = R.id.et_last_name;
      MSPEditText etLastName = ViewBindings.findChildViewById(rootView, id);
      if (etLastName == null) {
        break missingId;
      }

      id = R.id.et_password;
      MSPEditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.ll_terms_and_condition;
      LinearLayout llTermsAndCondition = ViewBindings.findChildViewById(rootView, id);
      if (llTermsAndCondition == null) {
        break missingId;
      }

      id = R.id.til_confirm_password;
      TextInputLayout tilConfirmPassword = ViewBindings.findChildViewById(rootView, id);
      if (tilConfirmPassword == null) {
        break missingId;
      }

      id = R.id.til_email;
      TextInputLayout tilEmail = ViewBindings.findChildViewById(rootView, id);
      if (tilEmail == null) {
        break missingId;
      }

      id = R.id.til_first_name;
      TextInputLayout tilFirstName = ViewBindings.findChildViewById(rootView, id);
      if (tilFirstName == null) {
        break missingId;
      }

      id = R.id.til_last_name;
      TextInputLayout tilLastName = ViewBindings.findChildViewById(rootView, id);
      if (tilLastName == null) {
        break missingId;
      }

      id = R.id.til_password;
      TextInputLayout tilPassword = ViewBindings.findChildViewById(rootView, id);
      if (tilPassword == null) {
        break missingId;
      }

      id = R.id.toolbar_register_activity;
      Toolbar toolbarRegisterActivity = ViewBindings.findChildViewById(rootView, id);
      if (toolbarRegisterActivity == null) {
        break missingId;
      }

      id = R.id.tv_already_have_an_account;
      MSPTextView tvAlreadyHaveAnAccount = ViewBindings.findChildViewById(rootView, id);
      if (tvAlreadyHaveAnAccount == null) {
        break missingId;
      }

      id = R.id.tv_login;
      MSPTextViewBold tvLogin = ViewBindings.findChildViewById(rootView, id);
      if (tvLogin == null) {
        break missingId;
      }

      id = R.id.tv_terms_condition;
      MSPTextView tvTermsCondition = ViewBindings.findChildViewById(rootView, id);
      if (tvTermsCondition == null) {
        break missingId;
      }

      id = R.id.tv_title;
      MSPTextViewBold tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ConstraintLayout) rootView, btnRegister,
          cbTermsAndCondition, etConfirmPassword, etEmail, etFirstName, etLastName, etPassword,
          llTermsAndCondition, tilConfirmPassword, tilEmail, tilFirstName, tilLastName, tilPassword,
          toolbarRegisterActivity, tvAlreadyHaveAnAccount, tvLogin, tvTermsCondition, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
