class CreateProfiles < ActiveRecord::Migration[5.1]
  def change
    create_table :profiles do |t|
      t.string :name
      t.string :profile_type
      t.string :mobile
      t.string :address
      t.string :about
      t.string :gender
      t.string :education
      t.references :user, foreign_key: true
      t.timestamps
    end
  end
end
