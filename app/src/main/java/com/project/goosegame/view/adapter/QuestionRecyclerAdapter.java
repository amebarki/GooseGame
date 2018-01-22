package com.project.goosegame.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.goosegame.R;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.CustomItemClickListener;

import java.util.List;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.QuestionHolder> {

    private List<Question> questionList;
    private CustomItemClickListener listener;

    public QuestionRecyclerAdapter(List<Question> questionList, CustomItemClickListener listener) {
        this.questionList = questionList;
        this.listener = listener;
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.question_recycler_item, viewGroup, false);

        final QuestionHolder holder = new QuestionHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(QuestionHolder holder, int position) {
        holder.questionTextView.setText(questionList.get(position).getTitle());
        holder.answerTrueTextView.setText(questionList.get(position).getCorrectAnswer());
        holder.answerFalse1TextView.setText(questionList.get(position).getFalseAnswerOne());
        holder.answerFalse2TextView.setText(questionList.get(position).getFalseAnswerTwo());
        holder.answerFalse3TextView.setText(questionList.get(position).getFalseAnswerThree());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionHolder extends RecyclerView.ViewHolder {
        public TextView questionTextView;
        public TextView answerTrueTextView;
        public TextView answerFalse1TextView;
        public TextView answerFalse2TextView;
        public TextView answerFalse3TextView;

        public QuestionHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.question_recycler_item_question);
            answerTrueTextView = itemView.findViewById(R.id.question_recycler_item_answer_true);
            answerFalse1TextView = itemView.findViewById(R.id.question_recycler_item_answer_false1);
            answerFalse2TextView = itemView.findViewById(R.id.question_recycler_item_answer_false2);
            answerFalse3TextView = itemView.findViewById(R.id.question_recycler_item_answer_false3);
        }
    }
}