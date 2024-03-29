// Generated by view binder compiler. Do not edit!
package com.krashkrosh748199.shoption.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.krashkrosh748199.shoption.R;
import com.krashkrosh748199.shoption.utils.MSPTextView;
import com.krashkrosh748199.shoption.utils.MSPTextViewBold;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemDashboardLayoutBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView ivDashboardItemImage;

  @NonNull
  public final LinearLayout llDashboardItemDetails;

  @NonNull
  public final MSPTextView tvDashboardItemPrice;

  @NonNull
  public final MSPTextViewBold tvDashboardItemTitle;

  @NonNull
  public final View viewDivider;

  private ItemDashboardLayoutBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageView ivDashboardItemImage, @NonNull LinearLayout llDashboardItemDetails,
      @NonNull MSPTextView tvDashboardItemPrice, @NonNull MSPTextViewBold tvDashboardItemTitle,
      @NonNull View viewDivider) {
    this.rootView = rootView;
    this.ivDashboardItemImage = ivDashboardItemImage;
    this.llDashboardItemDetails = llDashboardItemDetails;
    this.tvDashboardItemPrice = tvDashboardItemPrice;
    this.tvDashboardItemTitle = tvDashboardItemTitle;
    this.viewDivider = viewDivider;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemDashboardLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemDashboardLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_dashboard_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemDashboardLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iv_dashboard_item_image;
      ImageView ivDashboardItemImage = ViewBindings.findChildViewById(rootView, id);
      if (ivDashboardItemImage == null) {
        break missingId;
      }

      id = R.id.ll_dashboard_item_details;
      LinearLayout llDashboardItemDetails = ViewBindings.findChildViewById(rootView, id);
      if (llDashboardItemDetails == null) {
        break missingId;
      }

      id = R.id.tv_dashboard_item_price;
      MSPTextView tvDashboardItemPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvDashboardItemPrice == null) {
        break missingId;
      }

      id = R.id.tv_dashboard_item_title;
      MSPTextViewBold tvDashboardItemTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvDashboardItemTitle == null) {
        break missingId;
      }

      id = R.id.view_divider;
      View viewDivider = ViewBindings.findChildViewById(rootView, id);
      if (viewDivider == null) {
        break missingId;
      }

      return new ItemDashboardLayoutBinding((RelativeLayout) rootView, ivDashboardItemImage,
          llDashboardItemDetails, tvDashboardItemPrice, tvDashboardItemTitle, viewDivider);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
