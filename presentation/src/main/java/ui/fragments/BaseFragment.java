package ui.fragments;

import android.os.Build;

import androidx.fragment.app.Fragment;

import internal.di.HasComponent;
import ui.MainActivity;

public class BaseFragment extends Fragment {


    public MainActivity activity() {
        return ((MainActivity) getActivity());
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


}
