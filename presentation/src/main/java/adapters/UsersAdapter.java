package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.User;
import ru.production.test.R;

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> users;

    private Context context;

    private  OnBottomReachedListener onBottomReachedListener;

    private static final int FOOTER_VIEW = 1;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onUserItemClicked(User user);
    }

    public UsersAdapter(Context context) {
        this.context = context;
        users = new ArrayList<User>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        if (viewType == FOOTER_VIEW) {
            v = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
            FooterViewHolder vh = new FooterViewHolder(v);
            return vh;
        }

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != users.size()) {
            UserViewHolder vh = (UserViewHolder) holder;

            vh.title.setText(users.get(position).getTitle());
            vh.subtitle.setText(users.get(position).getSubtitle());
            Glide.with(context).load(users.get(position).getImage()).into(vh.image);

            vh.container.setOnClickListener(v -> {
                if (UsersAdapter.this.onItemClickListener != null) {
                    UsersAdapter.this.onItemClickListener.onUserItemClicked(users.get(position));
                }
            });

            if (position == users.size() - 3) {
                onBottomReachedListener.onBottomReached(position);
            }
        }
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    @Override
    public int getItemCount() {
        return users.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == users.size()) {
            return FOOTER_VIEW;
        }

        return super.getItemViewType(position);
    }

    public void addUsers(List<User> newUsers){
        this.users.addAll(newUsers);
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image) ImageView image;
        @BindView(R.id.id) TextView subtitle;
        @BindView(R.id.login) TextView title;
        @BindView(R.id.container) LinearLayout container;

        UserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}