class CreateReviews < ActiveRecord::Migration[6.0]
  def change
    create_table :reviews do |t|
      t.float :rating
      t.string :review
      t.references :sender
      t.references :reciever
      t.timestamps
    end
  end
end
