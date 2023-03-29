package com.hysteria.practice.player.profile.meta;

import com.hysteria.practice.game.kit.Kit;
import com.hysteria.practice.game.kit.KitLoadout;
import lombok.Getter;
import lombok.Setter;

public class ProfileKitEditorData {

	@Getter @Setter private boolean active;
	@Setter private boolean rename;
	@Getter @Setter private Kit selectedKit;
	@Getter @Setter private KitLoadout selectedKitLoadout;

	public boolean isRenaming() {
		return this.active && this.rename && this.selectedKit != null;
	}

}
