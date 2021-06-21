package com.kibtechnologies.captainshieid.views.components;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kibtechnologies.captainshieid.R;


public class CustomProgressDialog extends ProgressDialog {


  private final Context mContext;

  public CustomProgressDialog(Context context) {
    super(context, R.style.Custom_Progress_Dialog);
    this.mContext = context;
  }

  private Animation animation;

  private ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_custom_progress_loader);
    imageView = (ImageView) findViewById(R.id.image_icon);
    animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_rotate);
  }

  @Override
  public void show() {
    if (getWindow() != null && !isShowing() && mContext instanceof Activity) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        if (!((Activity) mContext).isFinishing() && !((Activity) mContext).isDestroyed()) {
          super.show();
          if (animation != null && imageView != null) imageView.startAnimation(animation);
        }
      } else if (!((Activity) mContext).isFinishing()) {
        super.show();
        if (animation != null && imageView != null) imageView.startAnimation(animation);
      }
    }
  }

  @Override
  public void dismiss() {
    if (animation != null) animation.cancel();
    try {
      super.dismiss();
    } catch (Exception ignore) {

    }
  }

  @Override
  public void hide() {
    if (animation != null) animation.cancel();
    super.hide();
  }

  @Override
  public void cancel() {
    if (animation != null) animation.cancel();
    super.cancel();
  }


  @Override
  public void setMessage(CharSequence message) {
    //super.setMessage(message);


  }

  public void destroy() {
    animation = null;
  }

}
