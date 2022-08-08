require 'json'
class DevicesController < ApplicationController
  def update
    if current_user.device.update(device_params)
      render json: current_user.device
    else
      render json: {error_message: "Please Check Your Parameters Again." },status: :bad_request
    end
  end
  def index
    device = Device.find_by(user_id: params[:user_id]).to_json(only: [:token])
    fruits = JSON.parse(device)['token']
    render json: fruits.to_json
  end


  def fcm_push_notification
    device = Device.find_by(user_id: params[:user_id]).to_json(only: [:token])
    token = JSON.parse(device)['token']
    puts token
    fcm_client = FCM.new("AAAAk8cd_7I:APA91bEIHaJOK9SuvseRa9MHBxk1cdWAuHkZNwi_GSrBEUvf9VHYvI8TrKWZ8PlVO6Hz-GKQqnocwplzlJV2gKYS6HWO37SynRITf1_GVIPzuYco6Jm2c1Vne4pMFC_CTXoUrPskgbP9") # set your FCM_SERVER_KEY
    options = { priority: 'high',
                data: { message: "HEY" },
                notification: {
                  title: "New Connect",
                  body: "Someone wants to connect with you",
                  sound: 'default',
                }
    }
    registration_ids = token
    response = fcm_client.send(registration_ids, options)
    render json:  response
  end
  private
    def device_params
      params.require(:device).permit(:token)
    end
end
