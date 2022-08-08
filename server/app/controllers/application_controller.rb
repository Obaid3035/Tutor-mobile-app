class ApplicationController < ActionController::API
  before_action :configure_permitted_parameters, if: :devise_controller?
  def render_resource(resource)
    if resource.errors.empty?
      # render json: resource
      render json: {status: "SUCCESS"}
    else
      validation_error(resource)
    end
  end

  def validation_error(resource)
    render json: {
      errors: [
                {
                  status: '400',
                  title: 'Bad Request',
                  detail: resource.errors,
                  code: '100'
                }
              ]
    }, status: :bad_request
  end
  protected

    def configure_permitted_parameters
      devise_parameter_sanitizer.permit(
        :sign_up, keys: [:email, :password,profile_attributes: [:name, :profile_type,
                                                                :mobile, :address,
                                                                :about, :gender,
                                                                :education]])
      end
end