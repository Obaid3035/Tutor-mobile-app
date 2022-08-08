class FriendshipsController < ApplicationController
  def all_friends
    @friends = Friendship.where(friend_id: current_user, status: "connected") + Friendship.where(user_id: current_user, status: "connected")
    render json:  @friends
  end
  def all_requests
    render json: Friendship.where(friend_id: current_user, status: 'requested') + Friendship.where(user_id: current_user, status: 'requested')
  end
  def index
    render json: Friendship.all
  end

  def friend_requests
    render json: current_user.requests
  end

  def show
    render json: current_user.friendships.find_by(friend_id: params[:friend_id])
  end

  def send_request
    friend_id = User.find(params[:friend_id])
    friendship = current_user.friendships.create(friend: friend_id)
    render json: friendship
  end

  def accept
    friendship = current_user.requests.find_by(user_id: params[:user_id])
    render json: friendship.update(status: :connected)
  end

  def decline
    friendship = current_user.requests.find_by(user_id: params[:user_id])
    friendship.destroy if friendship
    head :ok
  end

  def cancel
    friendship = current_user.friendships.requested.find_by(friend_id: params[:friend_id])
    friendship.destroy if friendship
    head :ok
  end

  def unfriend_sender
    friendship = current_user.friendships.connected.find_by(friend_id: params[:friend_id])
    friendship.destroy if friendship
    head :ok
  end

  def unfriend_reciever
    friendship = Friendship.find_by(friend_id: current_user,user_id: params[:user_id],status: :connected)
    friendship.destroy if friendship
    render json: friendship
  end

  def add
    friend = Friendship.where(friend_id: current_user,user_id: params[:user_id],status: :connected)
    friend2 = Friendship.where(friend_id: params[:user_id],user_id: current_user,status: :connected)
    req_rec = Friendship.where(friend_id: current_user,user_id: params[:user_id],status: :requested)
    req_sent = Friendship.where(friend_id: params[:user_id],user_id: current_user ,status: :requested)

    if !req_sent.empty?
      render json: {status: "Request_Sent"}
    else if !friend2.empty? || !friend.empty?
           render json: {status: "Friended"}
         else if !req_rec.empty?
                render json: {status: "Request_Received"}
              else
                render json: {status: "Not_Friend"}
              end
         end
    end
  end
end
