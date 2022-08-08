class User < ApplicationRecord
  has_one :profile
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable, :trackable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :validatable,
         :jwt_authenticatable, jwt_revocation_strategy: JwtBlacklist
  accepts_nested_attributes_for :profile
  #accepts_nested_attributes_for :device
  has_many :friendships
  has_many :chats
  has_one :device
  before_create :build_device
  has_many :requests, -> { where(status: :requested) }, class_name: 'Friendship', foreign_key: :friend_id


end
