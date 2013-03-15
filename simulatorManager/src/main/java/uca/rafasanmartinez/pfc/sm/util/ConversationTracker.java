package uca.rafasanmartinez.pfc.sm.util;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
/**
 * Bajo demanda del programa, guarda una conversación para futuras consultas
 * @author rafasanmartinez
 *
 */
@SuppressWarnings("serial")
@SessionScoped
public class ConversationTracker implements Serializable {
	
	private String conversationId;

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	
	
	
}
