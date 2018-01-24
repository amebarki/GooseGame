package com.project.goosegame.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.goosegame.R;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.CustomItemClickListener;
import com.project.goosegame.viewModel.QuestionsViewModel;

import java.util.List;

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.QuestionHolder> {

    private List<Question> questionList;
    private CustomItemClickListener listener;
    private QuestionsViewModel questionsViewModel;
    private Context context;

    public QuestionRecyclerAdapter(List<Question> questionList, QuestionsViewModel questionsViewModel, Context context, CustomItemClickListener listener) {
        this.questionList = questionList;
        this.listener = listener;
        this.questionsViewModel = questionsViewModel;
        this.context = context;
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
    public void onBindViewHolder(QuestionHolder holder, final int position) {
        holder.questionTextView.setText(questionList.get(position).getTitle());
        holder.answerTrueTextView.setText(questionList.get(position).getCorrectAnswer());
        holder.answerFalse1TextView.setText(questionList.get(position).getFalseAnswerOne());
        holder.answerFalse2TextView.setText(questionList.get(position).getFalseAnswerTwo());
        holder.answerFalse3TextView.setText(questionList.get(position).getFalseAnswerThree());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setPositiveButton("Supprimer",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                questionsViewModel.deleteQuestion(questionList.get(position));
                            }
                        });

                alertDialogBuilder.setNegativeButton("Annuler",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
        });
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
        public Button deleteButton;

        public QuestionHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.question_recycler_item_question);
            answerTrueTextView = itemView.findViewById(R.id.question_recycler_item_answer_true);
            answerFalse1TextView = itemView.findViewById(R.id.question_recycler_item_answer_false1);
            answerFalse2TextView = itemView.findViewById(R.id.question_recycler_item_answer_false2);
            answerFalse3TextView = itemView.findViewById(R.id.question_recycler_item_answer_false3);
            deleteButton = itemView.findViewById(R.id.question_recycler_item_button_delete);
        }
    }
}