import capsule

if __name__ == "__main__":
    time_capsule = capsule.Capsule()

    print("🕰️ Welcome to the Time Capsule 🎅")

    if time_capsule.has_past_message:
        print("\n📜 Message from your past self:")
        print(f"Written on: {time_capsule.timestamp.strftime('%B %d, %Y %H:%M:%S')}")
        print(f"💌 Message: {time_capsule.past_message}\n")
    else:
        print("\n📜 No message from your past self yet.")

    message = input("✍️  Enter a message for your future self: ").strip()

    time_capsule.save_message(message)

    print("\n🎉 Your message has been saved and added to the Time Capsule!")
    print("Opening the Time Capsule in your browser...\n")

    capsule.open_browser(capsule.Capsule.FILE_PATH)

    print("🌟 Thank you for participating in the Craft Advent Calendar! 🌟")
