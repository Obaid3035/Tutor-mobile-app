class Friendship < ApplicationRecord
  enum status: [ :requested, :connected]
  belongs_to :user
  belongs_to :friend, class_name: 'User'
end
