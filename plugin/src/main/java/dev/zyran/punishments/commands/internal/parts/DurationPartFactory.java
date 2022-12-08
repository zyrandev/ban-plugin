package dev.zyran.punishments.commands.internal.parts;

import dev.zyran.punishments.util.DurationParser;
import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.annotated.part.PartFactory;
import me.fixeddev.commandflow.exception.ArgumentParseException;
import me.fixeddev.commandflow.part.CommandPart;
import me.fixeddev.commandflow.stack.ArgumentStack;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.util.List;

public class DurationPartFactory
		implements PartFactory {
	@Override
	public CommandPart createPart(final String name, final List<? extends Annotation> modifiers) {
		return new CommandPart() {
			@Override
			public String getName() {
				return name;
			}

			@Override
			public void parse(final CommandContext context, final ArgumentStack stack,
							  @Nullable final CommandPart caller)
					throws ArgumentParseException {
				final String argument = stack.next();
				Duration duration;
				if (argument.equalsIgnoreCase("permanent")) {
					duration = null;
				} else {
					try {
						duration = DurationParser.parseDuration(argument);
					} catch (final IllegalArgumentException exception) {
						throw new ArgumentParseException(exception.getMessage());
					}
				}
				context.setValue(this, duration);
			}
		};
	}
}
