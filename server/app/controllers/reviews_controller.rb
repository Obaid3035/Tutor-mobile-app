class ReviewsController < ApplicationController

  def create_review
    @reciever = User.find(params[:reciever_id])
    @create_review = Review.new(
      rating: params[:rating].to_f,
      review: params[:review],
      sender_id: current_user.id,
      reciever_id: @reciever.id)
    @create_review.save!
    render json: @create_review
  end
  private

end
