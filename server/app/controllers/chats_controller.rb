class ChatsController < ApplicationController
  def send_message
    @reciever = User.find(params[:reciever_id])
    @send_message = Chat.new(
      message: params[:message],
      sender_id: current_user.id,
      reciever_id: @reciever.id)
    @send_message.save!
    render json: @send_message
  end

  def all_chat
    @reciever = User.find(params[:reciever_id])
    @chats = Chat.where( sender_id: current_user.id,reciever_id: @reciever.id) + Chat.where(sender_id: @reciever.id , reciever_id: current_user.id )
    render json: @chats
  end

end
