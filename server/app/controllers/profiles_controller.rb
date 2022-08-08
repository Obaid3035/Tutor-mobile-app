class ProfilesController < ApplicationController
  def index
    @type = params[:profile_type]
    if @type
      profile = Profile.where(profile_type: params[:profile_type])
    else
      profile = current_user.profile
    end
    render json: profile
  end

  def show
    user = User.find(params[:id])
      render json: user.profile
  end
end
