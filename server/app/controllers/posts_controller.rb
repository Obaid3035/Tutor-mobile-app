class PostsController < ApplicationController
  def index
    @type = params[:profile_type]
    @timing = params[:timing]
    @subject = params[:subject]
    if (@timing && @type && @subject) #FILTERS
      posts = Post.where(subject: @subject, timing: @timing).joins(:profile).where(profiles: {profile_type: @type})
      @posts = posts.to_json(:include => [:profile]) 
    else if (@type && @subject)
           posts = Post.where(subject: @subject).joins(:profile).where(profiles: {profile_type: @type})
           @posts = posts.to_json(:include => [:profile]) 
         else if (@type && @timing)
                posts = Post.where(timing: @timing).joins(:profile).where(profiles: {profile_type: @type})
                @posts = posts.to_json(:include => [:profile]) 
              else if (@type)
                       posts = Post.joins(:profile).where(profiles: {profile_type: @type})
                       @posts = posts.to_json(:include => [:profile]) 
                   else
                       @posts = {status: "NOT FOUND"} 
                   end 
              end
         end

    end
    render json: @posts
  end

  def current_post
    render json: current_user.profile.posts.to_json(:include => [:profile])

  end

  def create
    profile = current_user.profile
    post = profile.posts.create(post_params)
    if post.save
      render json: post
    else
      render json: {error_message: "Please Check Your Parameters Again." },status: :bad_request
    end
  end
  private
  def post_params
    params.require(:post).permit( :subject, :timing, :body)
  end
end
