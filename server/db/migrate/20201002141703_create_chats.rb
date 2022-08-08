class CreateChats < ActiveRecord::Migration[6.0]
  def change
    create_table :chats do |t|
      t.string :message
      t.references :sender
      t.references :reciever

      t.timestamps
    end
  end
end
