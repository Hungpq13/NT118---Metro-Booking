//package com.example.nt118project.bottomnav;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.lifecycle.Lifecycle;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//
//public class navadapter extends FragmentStateAdapter {
//
//    public navadapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//    }
//
//    public navadapter(@NonNull Fragment fragment) {
//        super(fragment);
//    }
//
//    public navadapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
//        super(fragmentManager, lifecycle);
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                return new menu();
//            case 1:
//                return new favorite();
//
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}
