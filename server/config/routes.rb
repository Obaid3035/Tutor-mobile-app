Rails.application.routes.draw do
  devise_for :users,
             path: '',
             path_names: {
               sign_in: 'login',
               sign_out: 'logout',
               registration: 'signup'
             },
             controllers: {
               sessions: 'sessions',
               registrations: 'registrations'
             }
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  get 'friendship/:user_id', to: 'friendships#add'
  get 'profile', to: 'profiles#index'
  get 'profile/:id', to: 'profiles#show'
  post 'post', to: 'posts#create'
  get 'posts', to: 'posts#index'
  get 'friends', to: "friendships#all_friends"
  get 'allrequest', to: "friendships#all_requests"
  get 'request', to: "friendships#friend_requests"
  get 'show/:friend_id', to: "friendships#show"
  get 'send/:friend_id', to: "friendships#send_request"
  get 'accept/:user_id', to: "friendships#accept"
  get 'decline/:user_id', to: "friendships#decline"
  get 'cancel/:friend_id', to: "friendships#cancel"
  get 'unfriendsend/:friend_id', to: "friendships#unfriend_sender"
  get 'unfriendreceive/:user_id', to: "friendships#unfriend_reciever"
  get 'all', to: "friendships#index"
  get 'notify/:user_id', to: "devices#fcm_push_notification"
  put 'devicetoken', to: "devices#update"
  get 'showtoken/:user_id',  to: "devices#index"
  post 'send_chat/:reciever_id', to: "chats#send_message"
  get 'all_chat/:reciever_id', to: "chats#all_chat"
  get 'current_posts', to: "posts#current_post"
  post 'review/:reciever_id', to: 'reviews#create_review'

end
