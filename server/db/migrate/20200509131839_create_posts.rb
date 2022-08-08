class CreatePosts < ActiveRecord::Migration[5.1]
  def change
    create_table :posts do |t|
      t.string :subject
      t.string :timing
      t.string :body
      t.references :profile, foreign_key: true

      t.timestamps
    end
  end
end
